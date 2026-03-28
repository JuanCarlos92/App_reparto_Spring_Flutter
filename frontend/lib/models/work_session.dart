// ignore_for_file: avoid_print

import 'dart:convert';

class WorkSession {
  final int? id;
  final DateTime startTime;
  final DateTime? endTime;
  final Duration workedTime;
  final String status;

  // Constructor
  WorkSession({
    this.id,
    required this.startTime,
    this.endTime,
    required this.workedTime,
    required this.status,
  });

  String _fmt(DateTime dt) {
    final s = dt.toIso8601String();
    final i = s.indexOf('.');
    return i == -1 ? s : s.substring(0, i);
  }

  // Método para inicio de sesión
  Map<String, dynamic> toJsonStart() {
    final fechaFormateada = _fmt(startTime);
    final data = {
      'startDate': fechaFormateada,
      'status': status.toUpperCase(),
    };

    print('Fecha enviada: $fechaFormateada');
    print('JSON a enviar: ${json.encode(data)}');

    return data;
  }

  // Método para finalizar sesión
  Map<String, dynamic> toJsonEnd() {
    return {
      if (id != null) 'id': id,
      'startDate': _fmt(startTime),
      'endDate': endTime == null ? null : _fmt(endTime!),
      'status': status.toUpperCase(),
    };
  }

  // Método para actualizar estado
  Map<String, dynamic> toJsonUpdate() {
    return {
      'status': status.toUpperCase(),
    };
  }

  // Método general con todos los datos
  Map<String, dynamic> toJson() {
    return {
      if (id != null) 'id': id,
      'startDate': _fmt(startTime),
      'endDate': endTime == null ? null : _fmt(endTime!),
      'status': status.toUpperCase(),
    };
  }
}
