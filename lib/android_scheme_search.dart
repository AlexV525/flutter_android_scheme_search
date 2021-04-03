///
/// [Author] Alex (https://github.com/AlexV525)
/// [Date] 2021-04-03 22:45
///
import 'dart:async';

import 'package:flutter/services.dart';

class AndroidSchemeSearch {
  static const MethodChannel _channel = MethodChannel('android_scheme_search');

  static Future<String?> search(String url) =>
      _channel.invokeMethod('search', <String, dynamic>{'url': url});
}
