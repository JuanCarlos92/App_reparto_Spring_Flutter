// ignore_for_file: avoid_print
import 'package:app_reparto/core/config/backend.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../local/token_service.dart';
// import 'work_session_service.dart';

class AuthService {
  // Método login
  Future<Map<String, dynamic>> login(String username, String password) async {
    try {
      print('[AUTH] Iniciando proceso de login para usuario: $username');
      print('Login: $username, Password: $password');

      final response = await http.post(
        Uri.parse('${Backend.url}/api/login'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode({
          'username': username,
          'password': password,
        }),
      );

      print('[AUTH] Código de respuesta: ${response.statusCode}');
      print('[AUTH] Cuerpo de la respuesta: ${response.body}');

      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        print('[AUTH] Login exitoso - Procesando respuesta');
        print('[AUTH] Cuerpo de la respuesta decodificado: $data');

        // Ajuste para formato JWT actual
        if (data.containsKey('token')) {
          String token = data['token'];
          await TokenService.setToken(token);

          return data;
        } else {
          throw Exception('Token no encontrado en la respuesta');
        }
       } else if (response.statusCode == 401) {
        await TokenService.clearToken();
        throw Exception('Credenciales inválidas');
      } else {
        throw Exception('Error: ${response.statusCode}');
      }
    } catch (e) {
      print('[AUTH] Error 401 durante el login: $e');
      throw Exception('$e');
    }
  }
}
