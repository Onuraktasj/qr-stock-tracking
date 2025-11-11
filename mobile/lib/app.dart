import 'package:flutter/material.dart';

import 'ui/screens/home_screen.dart';
import 'ui/screens/login_screen.dart';

class QrStockApp extends StatelessWidget {
  const QrStockApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'QR Stock Tracking',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
        useMaterial3: true,
        textTheme: Theme.of(context).textTheme.apply(fontSizeFactor: 1.1),
      ),
      home: const LoginScreen(),
      routes: {
        HomeScreen.routeName: (context) => const HomeScreen(),
      },
    );
  }
}


