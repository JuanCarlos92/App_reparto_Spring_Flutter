import 'dart:convert';

// Funciones auxiliares para convertir entre JSON y objetos Client
Client clientFromJson(String str) => Client.fromJson(json.decode(str));
String clientToJson(Client data) => json.encode(data.toJson());

class Client {
  String id;
  String name;
  String town;
  String address;
  double latitude;
  double longitude;
  int durationInSeconds;

  // Constructor
  Client({
    required this.id,
    required this.name,
    required this.town,
    required this.address,
    required this.latitude,
    required this.longitude,
    this.durationInSeconds = 0,
  });

static Client fromApiResponse(Map<String, dynamic> item) {
  double lat = 0.0;
  double lng = 0.0;

  if (item['latitude'] != null) {
    lat = item['latitude'] is num
        ? (item['latitude'] as num).toDouble()
        : double.tryParse(item['latitude'].toString()) ?? 0.0;
  }

  if (item['longitude'] != null) {
    lng = item['longitude'] is num
        ? (item['longitude'] as num).toDouble()
        : double.tryParse(item['longitude'].toString()) ?? 0.0;
  }

  return Client(
    id: (item['id'] ?? '').toString(),
    name: (item['name'] ?? '').toString(),
    town: (item['town'] ?? '').toString(),
    address: (item['address'] ?? '').toString(),
    latitude: lat,
    longitude: lng,
  );
}

  // Método para convertir un Map (JSON) a una instancia de Client
  factory Client.fromJson(Map<String, dynamic> json) {
    double parseLat = 0.0;
    double parseLong = 0.0;

    try {
      if (json["latitud"] != null) {
        parseLat = json["latitud"] is double
            ? json["latitud"]
            : double.parse(json["latitud"].toString());
      }
      if (json["longitud"] != null) {
        parseLong = json["longitud"] is double
            ? json["longitud"]
            : double.parse(json["longitud"].toString());
      }
    } catch (e) {
      // ignore: avoid_print
      print('Error - parsing coordinates: $e');
    }

    // Crear una instancia de Client con los datos del Map
    return Client(
      id: json["id"]?.toString() ?? '',
      name: json["name"]?.toString() ?? '',
      town: json["town"]?.toString() ?? '',
      address: json["address"]?.toString() ?? '',
      latitude: parseLat,
      longitude: parseLong,
      durationInSeconds: json["durationInSeconds"] ?? 0,
    );
  }

  // Método para convertir la instancia a un Map (JSON)
  Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
        "town": town,
        "address": address,
        "latitud": latitude,
        "longitud": longitude,
      };

  @override
  String toString() {
    return '''
    ID: $id
    Nombre: $name
    Ciudad: $town
    Dirección: $address
    Latitud: $latitude
    Longitud: $longitude
    ''';
  }
}
