import 'dart:io';

import 'package:drift/drift.dart';
import 'package:drift/native.dart';
import 'package:path/path.dart' as p;
import 'package:path_provider/path_provider.dart';

part 'database.g.dart';

class PendingTxn extends Table {
  IntColumn get id => integer().autoIncrement()();
  TextColumn get endpoint => text()();
  TextColumn get payload => text()();
  IntColumn get retryCount => integer().withDefault(const Constant(0))();
  DateTimeColumn get createdAt => dateTime().withDefault(currentDateAndTime)();
}

@DriftDatabase(tables: [PendingTxn])
class LocalDatabase extends _$LocalDatabase {
  LocalDatabase() : super(_openConnection());

  @override
  int get schemaVersion => 1;

  Future<int> enqueue(String endpoint, String payload) {
    return into(pendingTxn).insert(PendingTxnCompanion.insert(endpoint: endpoint, payload: payload));
  }

  Stream<List<PendingTxnData>> watchQueue() {
    return (select(pendingTxn)..orderBy([(tbl) => OrderingTerm.asc(tbl.id)])).watch();
  }

  Future<void> deleteItem(int id) {
    return (delete(pendingTxn)..where((tbl) => tbl.id.equals(id))).go();
  }

  Future<void> incrementRetry(int id) {
    return customStatement('UPDATE pending_txn SET retry_count = retry_count + 1 WHERE id = ?', [id]);
  }
}

LazyDatabase _openConnection() {
  return LazyDatabase(() async {
    final dir = await getApplicationDocumentsDirectory();
    final file = File(p.join(dir.path, 'qr_stock.sqlite'));
    return NativeDatabase(file);
  });
}

