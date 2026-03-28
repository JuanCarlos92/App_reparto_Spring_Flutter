import 'package:flutter_dotenv/flutter_dotenv.dart';

class Api {
  // Base URL de GraphHopper
  static const String graphHopperApi = 'https://graphhopper.com/api/1/route';
  
  // Tu API key se obtiene desde .env (corregido el nombre)
  static final String graphHopperKey = dotenv.env['GRAPHOPPER_KEY'] ?? '';
}
