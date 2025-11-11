import 'dart:convert';

import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../data/local/database.dart';

final localDatabaseProvider = Provider<LocalDatabase>((_) => LocalDatabase());

class PendingTransactionQueue {
  PendingTransactionQueue(this._db);

  final LocalDatabase _db;

  Future<void> enqueue(String endpoint, Map<String, dynamic> payload) {
    return _db.enqueue(endpoint, jsonEncode(payload));
  }
}

final pendingQueueProvider = Provider<PendingTransactionQueue>((ref) {
  final db = ref.watch(localDatabaseProvider);
  return PendingTransactionQueue(db);
});


