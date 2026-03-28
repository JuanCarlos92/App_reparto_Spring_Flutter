// ignore_for_file: unnecessary_overrides, deprecated_member_use

import 'dart:async';

import 'package:app_reparto/widgets/timer_widget.dart';
import 'package:app_reparto/providers/clients_provider.dart';
import 'package:app_reparto/providers/pomodoro_provider.dart';
import 'package:app_reparto/providers/timer_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../widgets/bar_widget.dart';
import '../widgets/list_widget.dart';

class VisitsScreen extends StatefulWidget {
  const VisitsScreen({super.key});

  @override
  State<VisitsScreen> createState() => _VisitsScreenState();
}

class _VisitsScreenState extends State<VisitsScreen> with RouteAware {
  late TimerProvider _timerProvider;
  Timer? _timer;
  late RouteObserver<PageRoute> routeObserver;

  @override
  void initState() {
    super.initState();
    routeObserver = RouteObserver<PageRoute>();

    WidgetsBinding.instance.addPostFrameCallback((_) {
      if (!mounted) return;
      final clientsProvider = Provider.of<ClientsProvider>(context, listen: false);
      clientsProvider.fetchClientsFromBackend();

      final timerProvider = Provider.of<TimerProvider>(context, listen: false);
      final pomodoroProvider =
          Provider.of<PomodoroProvider>(context, listen: false);
      timerProvider.setPomodoroProvider(pomodoroProvider);

      if (!timerProvider.isRunning &&
          !timerProvider.hasStartedSent &&
          timerProvider.hours == 0 &&
          timerProvider.minutes == 0 &&
          timerProvider.seconds == 0) {
        timerProvider.iniciarTimer();
      }
    });
  }

  @override
  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    routeObserver.subscribe(this, ModalRoute.of(context) as PageRoute);

    _timerProvider = Provider.of<TimerProvider>(context, listen: false);
    final pomodoroProvider =
        Provider.of<PomodoroProvider>(context, listen: false);
    _timerProvider.setPomodoroProvider(pomodoroProvider);

    final arguments =
        ModalRoute.of(context)?.settings.arguments as Map<String, dynamic>?;

    // Inicia o reanuda el temporizador automáticamente según los argumentos
    if (arguments != null) {
      if (arguments['resumeTimer'] == true) {
        Future.microtask(() {
          if (mounted) {
            _timerProvider.reanudarTimer();
          }
        });
      } else if (arguments['startTimer'] == true) {
        if (!_timerProvider.isRunning &&
            !_timerProvider.hasStartedSent &&
            _timerProvider.hours == 0 &&
            _timerProvider.minutes == 0 &&
            _timerProvider.seconds == 0) {
          Future.microtask(() {
            if (mounted) {
              _timerProvider.iniciarTimer();
            }
          });
        }
      }
    }

    // Calcular tiempos al entrar por argumentos (controlado en initState/didPopNext)
  }

  @override
  void didPopNext() {
    if (!mounted) return;
    // Se ejecuta cuando se regresa a esta pantalla
    final clientsProvider = context.read<ClientsProvider>();

    // Ejecutamos las operaciones asíncronas de manera segura
    clientsProvider.fetchClientsFromBackend();
  }

  @override
  void didPushNext() {
    // Se ejecuta cuando se navega a otra pantalla
    _timer?.cancel();
  }

  @override
  void didPop() {
    super.didPop();
  }

  @override
  void dispose() {
    routeObserver.unsubscribe(this);
    _timer?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: const BoxDecoration(
          color: Colors.white,
        ),
        child: Column(
          children: [
            // Botón de retroceso
            Padding(
              padding: const EdgeInsets.only(top: 10, left: 10),
              child: Align(
                alignment: Alignment.centerLeft,
                child: IconButton(
                  icon: const Icon(
                    Icons.arrow_back,
                    color: Colors.black,
                    size: 30,
                  ),
                  onPressed: () => Navigator.of(context).pop(),
                ),
              ),
            ),
            // Padding superior para el logo
            Padding(
              padding: const EdgeInsets.only(bottom: 5),
              child: Image.asset(
                'assets/reparto360.png',
                height: 60,
                fit: BoxFit.contain,
              ),
            ),
            // Sección superior con el widget del temporizador
            Container(
              padding: const EdgeInsets.fromLTRB(25, 20, 25, 20),
              child: const Column(
                children: [
                  TimerWidget(),
                ],
              ),
            ),
            const SizedBox(height: 5),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 25),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    'Ruta del día',
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                      color: Colors.black,
                      fontFamily: 'Roboto',
                    ),
                  ),
                  Consumer<ClientsProvider>(
                    builder: (context, provider, child) {
                      final isBusy = provider.isLoading || provider.isUpdatingDurations || provider.clients.isEmpty;
                      return Container(
                        decoration: BoxDecoration(
                          color: const Color(0xFFFFF3E0),
                          borderRadius: BorderRadius.circular(12),
                          boxShadow: [
                            BoxShadow(
                              color: Colors.black.withOpacity(0.1),
                              spreadRadius: 1,
                              blurRadius: 4,
                              offset: const Offset(0, 2),
                            ),
                          ],
                        ),
                        child: TextButton(
                          onPressed: isBusy
                              ? null
                              : () async {
                                  await provider.updateClientsWithDuration(force: true);
                                },
                          style: TextButton.styleFrom(
                            backgroundColor: Colors.transparent,
                            foregroundColor: Colors.black,
                            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(12),
                            ),
                          ),
                          child: const Text(
                            'Calcular tiempo',
                            style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      );
                    },
                  ),
                ],
              ),
            ),
            // Lista de clientes directamente sin contenedor
            Expanded(
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 25),
                child: ListWidget(),
              ),
            ),
          ],
        ),
      ),
      bottomNavigationBar: const BarWidget(),
    );
  }
}


    //         // Contenedor principal con la lista de visitas
    //         Expanded(
    //           child: Center(
    //             child: SizedBox(
    //               width: MediaQuery.of(context).size.width *
    //                   0.9, // 90% del ancho de la pantalla
    //               child: Container(
    //                 // Decoración del contenedor de la lista
    //                 decoration: BoxDecoration(
    //                   color: Colors.white,
    //                   borderRadius: const BorderRadius.all(Radius.circular(40)),
    //                   boxShadow: [
    //                     BoxShadow(
    //                       // ignore: deprecated_member_use
    //                       color: Colors.black.withOpacity(0.1),
    //                       blurRadius: 20,
    //                       spreadRadius: 5,
    //                     ),
    //                   ],
    //                   border: Border.all(
    //                     color: const Color.fromARGB(255, 200, 120, 20),
    //                     width: 1.5,
    //                   ),
    //                 ),
    //                 // Widget personalizado que muestra la lista de clientes
    //                 child: const Padding(
    //                   padding:
    //                       EdgeInsets.symmetric(horizontal: 20, vertical: 30),
    //                   child:
    //                       // Widget que renderiza la lista de clientes
    //                       ListWidget(),
    //                 ),
    //               ),
    //             ),
    //           ),
    //         ),
    //         const SizedBox(height: 20),
    //       ],
    //     ),
    //   ),
    // );
