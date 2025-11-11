import 'dart:convert';

import 'package:flutter/material.dart';

import 'qr_scanner_screen.dart';

enum TransactionType { inbound, move, outbound, count, alloc, report }

class TransactionScreen extends StatelessWidget {
  const TransactionScreen({super.key, required this.type});

  final TransactionType type;

  String get _title {
    switch (type) {
      case TransactionType.inbound:
        return 'Stock IN';
      case TransactionType.move:
        return 'Stock MOVE';
      case TransactionType.outbound:
        return 'Stock OUT';
      case TransactionType.count:
        return 'Stock COUNT';
      case TransactionType.alloc:
        return 'Allocate';
      case TransactionType.report:
        return 'Reports';
    }
  }

  @override
  Widget build(BuildContext context) {
    return _TransactionBody(title: _title);
  }
}

class _TransactionBody extends StatefulWidget {
  const _TransactionBody({required this.title});

  final String title;

  @override
  State<_TransactionBody> createState() => _TransactionBodyState();
}

class _TransactionBodyState extends State<_TransactionBody> {
  String? _lastScan;
  String? _error;

  Future<void> _scanQr() async {
    final result = await Navigator.of(context).push<String>(
      MaterialPageRoute(builder: (_) => const QrScannerScreen()),
    );
    if (!mounted) return;
    if (result == null) return;
    try {
      final decoded = jsonDecode(result);
      setState(() {
        _lastScan = const JsonEncoder.withIndent('  ').convert(decoded);
        _error = null;
      });
    } on FormatException catch (_) {
      setState(() {
        _lastScan = result;
        _error = 'JSON formatında değil';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(widget.title)),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Expanded(
              child: Card(
                elevation: 3,
                child: Padding(
                  padding: const EdgeInsets.all(24),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      Icon(Icons.qr_code_scanner,
                          size: 96, color: Theme.of(context).colorScheme.primary),
                      const SizedBox(height: 16),
                      Text(
                        'QR Tara',
                        style: Theme.of(context).textTheme.headlineSmall,
                        textAlign: TextAlign.center,
                      ),
                      const SizedBox(height: 12),
                      const Text(
                        'Paket üzerindeki QR kodu tarayarak stok işlemini başlatın.',
                        textAlign: TextAlign.center,
                      ),
                      const Spacer(),
                      if (_lastScan != null)
                        Expanded(
                          child: SingleChildScrollView(
                            child: Text(
                              _lastScan!,
                              style: const TextStyle(fontFamily: 'monospace'),
                            ),
                          ),
                        ),
                      if (_error != null)
                        Padding(
                          padding: const EdgeInsets.only(top: 8),
                          child: Text(
                            _error!,
                            style: TextStyle(color: Theme.of(context).colorScheme.error),
                          ),
                        ),
                    ],
                  ),
                ),
              ),
            ),
            const SizedBox(height: 16),
            ElevatedButton.icon(
              icon: const Icon(Icons.qr_code_2),
              label: const Text('QR Kod Tara'),
              onPressed: _scanQr,
            ),
          ],
        ),
      ),
    );
  }
}


