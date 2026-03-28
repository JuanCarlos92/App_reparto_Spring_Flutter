// ignore_for_file: avoid_print

import 'package:app_reparto/models/client.dart';
import 'package:geolocator/geolocator.dart';
import '../core/services/api/graphhopper_time_service.dart';
import 'package:app_reparto/core/services/local/geolocation_service.dart';
import '../core/services/backend/client_service.dart';
import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class ClientsProvider extends ChangeNotifier {
  // Servicios necesarios para la gestión de clientes y geolocalización
  final ClientService _clientService = ClientService();
  final GeolocationService _geolocationService = GeolocationService();
  final GraphhopperTimeService _graphhopperTimeService =
      GraphhopperTimeService();

  List<Client> _clients = [];
  bool _isLoading = false;
  String _error = '';
  bool _isUpdatingDurations = false;
  LatLng? _lastCalculatedPosition;
  List<Client> get clients => _clients;
  bool get isLoading => _isLoading;
  String get error => _error;
  bool get isUpdatingDurations => _isUpdatingDurations;

  Future<void> fetchClientsFromBackend() async {
    try {
      _isLoading = true;
      _error = '';
      notifyListeners();

      final fetchedClients = await _clientService.getClients();
      _clients = fetchedClients;
      for (final c in _clients) {
        c.durationInSeconds = -2;
      }
      
      notifyListeners();
    } catch (e) {
      _error = e.toString();
      notifyListeners();
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> updateClientsWithDuration({bool force = false}) async {
    if (_isUpdatingDurations || _clients.isEmpty) return;

    try {
      _isUpdatingDurations = true;
      if (force) {
        for (final c in _clients) {
          c.durationInSeconds = 0;
        }
        notifyListeners();
      }
      Position? position;
      try {
        position = await _geolocationService.determinarPosition();
      } catch (_) {
        position = await Geolocator.getLastKnownPosition();
      }
      if (position == null) {
        throw Exception('No se pudo obtener la posición actual');
      }

      final currentLatLng = LatLng(position.latitude, position.longitude);
      if (!force && _lastCalculatedPosition != null) {
        final dist = Geolocator.distanceBetween(
          _lastCalculatedPosition!.latitude,
          _lastCalculatedPosition!.longitude,
          currentLatLng.latitude,
          currentLatLng.longitude,
        );
        if (dist < 100) {
          _isUpdatingDurations = false;
          return;
        }
      }

      for (final client in _clients) {
        await _updateClientDuration(client, position);
        await Future.delayed(const Duration(milliseconds: 200));
      }

      // Ordenar la lista por tiempos de viaje
      _clients.sort((a, b) {
        if (a.durationInSeconds <= 0 && b.durationInSeconds <= 0) return 0;
        if (a.durationInSeconds <= 0) return 1;
        if (b.durationInSeconds <= 0) return -1;
        return a.durationInSeconds.compareTo(b.durationInSeconds);
      });

      _lastCalculatedPosition = currentLatLng;
      notifyListeners();
    } catch (e) {
      print('Error actualizando duraciones: $e');
    } finally {
      _isUpdatingDurations = false;
    }
  }

  Future<void> _updateClientDuration(Client client, Position position) async {
    try {
      client.durationInSeconds = 0;
      final duration = await _graphhopperTimeService.getEstimatedTime(
        LatLng(position.latitude, position.longitude),
        client.latitude,
        client.longitude,
      );
      client.durationInSeconds = duration ?? -1;
    } catch (e) {
      print('Error actualizando tiempo para cliente ${client.id}: $e');
      client.durationInSeconds = -1;
    }
  }
}
