import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static const platform = MethodChannel('web_blocker');

  const MyApp({super.key});

  Future<void> enableWebBlocker() async {
    try {
      await platform.invokeMethod('enableAccessibilityService');
    } on PlatformException catch (e) {
      print("Failed to enable service: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text('Parental Control')),
        body: Center(
          child: ElevatedButton(
            onPressed: enableWebBlocker,
            child: Text('Enable Website Blocking'),
          ),
        ),
      ),
    );
  }
}
