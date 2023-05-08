import 'package:flutter/material.dart';
import 'package:get/get.dart';

void showSnackBar(
    /*int? time,
    {required BuildContext context, required String content}*/
    BuildContext context,
    String content,
    {int? time}) {
  ScaffoldMessenger.of(context).showSnackBar(SnackBar(
    content: Text(content),
    duration: Duration(seconds: time ?? 5),
  ));
}