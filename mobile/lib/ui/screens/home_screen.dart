import 'package:flutter/material.dart';

import 'transaction_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  static const routeName = '/home';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Dashboard')),
      body: GridView.count(
        crossAxisCount: 2,
        padding: const EdgeInsets.all(16),
        mainAxisSpacing: 16,
        crossAxisSpacing: 16,
        children: const [
          _HomeTile(label: 'IN', screen: TransactionScreen(type: TransactionType.inbound)),
          _HomeTile(label: 'MOVE', screen: TransactionScreen(type: TransactionType.move)),
          _HomeTile(label: 'OUT', screen: TransactionScreen(type: TransactionType.outbound)),
          _HomeTile(label: 'COUNT', screen: TransactionScreen(type: TransactionType.count)),
          _HomeTile(label: 'ALLOC', screen: TransactionScreen(type: TransactionType.alloc)),
          _HomeTile(label: 'Reports', screen: TransactionScreen(type: TransactionType.report)),
        ],
      ),
    );
  }
}

class _HomeTile extends StatelessWidget {
  const _HomeTile({required this.label, required this.screen});

  final String label;
  final Widget screen;

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      style: ElevatedButton.styleFrom(padding: const EdgeInsets.all(24), textStyle: const TextStyle(fontSize: 20)),
      onPressed: () => Navigator.of(context).push(MaterialPageRoute(builder: (_) => screen)),
      child: Text(label),
    );
  }
}


