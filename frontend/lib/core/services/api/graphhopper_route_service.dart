// ignore_for_file: avoid_print
import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../../config/api.dart';

class GraphhopperRouteService {
  Future<Set<Polyline>> getRoute(
      LatLng currentPosition, double destLat, double destLng) async {
    final String url = '${Api.graphHopperApi}'
        '?point=${currentPosition.latitude},${currentPosition.longitude}'
        '&point=$destLat,$destLng'
        '&vehicle=car'
        '&locale=es'
        '&calc_points=true'
        '&points_encoded=true'
        '&key=${Api.graphHopperKey}'; // key desde Api.dart

    try {
      print('Solicitando ruta a: $url');
      final response = await http.get(Uri.parse(url));
      final data = json.decode(response.body);

      if (data['message']?.contains('API limit') ?? false) {
        print('Límite de API excedido');
        return <Polyline>{};
      }

      if (response.statusCode == 200 &&
          data['paths'] != null &&
          data['paths'].isNotEmpty) {
        final path = data['paths'][0];
        if (path['points'] == null) {
          print('Error: No se encontraron puntos en la respuesta');
          return <Polyline>{};
        }

        List<LatLng> points = [];
        if (path['points'] is Map && path['points']['coordinates'] != null) {
          points = _decodePolylineCoordinates(path['points']['coordinates']);
        } else if (path['points'] is String) {
          points = _decodePolylineEncoded(path['points'] as String);
        } else {
          return <Polyline>{};
        }
        return {
          Polyline(
            polylineId: const PolylineId('route'),
            points: points,
            color: Colors.blue,
            width: 5,
          ),
        };
      }

      print('Error - Respuesta de API inválida: ${response.body}');
      return <Polyline>{};
    } catch (e) {
      print('Error obteniendo ruta: $e');
      return <Polyline>{};
    }
  }

  List<LatLng> _decodePolylineCoordinates(List<dynamic> coordinates) {
    return coordinates
        .map((coord) => LatLng(coord[1], coord[0]))
        .toList();
  }

  List<LatLng> _decodePolylineEncoded(String encoded) {
    List<LatLng> points = [];
    int index = 0, len = encoded.length;
    int lat = 0, lng = 0;
    while (index < len) {
      int b, shift = 0, result = 0;
      do {
        b = encoded.codeUnitAt(index++) - 63;
        result |= (b & 0x1F) << shift;
        shift += 5;
      } while (b >= 0x20);
      int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
      lat += dlat;
      shift = 0;
      result = 0;
      do {
        b = encoded.codeUnitAt(index++) - 63;
        result |= (b & 0x1F) << shift;
        shift += 5;
      } while (b >= 0x20);
      int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
      lng += dlng;
      points.add(LatLng(lat / 1E5, lng / 1E5));
    }
    return points;
  }
}
