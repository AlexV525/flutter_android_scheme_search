import 'dart:async';

import 'package:flutter/material.dart';
import 'package:android_scheme_search/android_scheme_search.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String? appName = 'Unknown';

  @override
  void initState() {
    super.initState();
    search();
  }

  Future<void> search() async {
    appName = await AndroidSchemeSearch.search('http://www.google.com');
    if (mounted) {
      setState(() {});
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Plugin example app')),
        body: Center(child: Text('$appName')),
      ),
    );
  }
}
