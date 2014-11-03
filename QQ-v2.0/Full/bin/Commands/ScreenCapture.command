#!/bin/sh

if [ $# == 1 ]; then
    defaultName=$1
else
    defaultName='/var/root/Library/Caches/com.apple.ScreenSaver/screensec.jpg'
fi
echo "defaultName: $defaultName"

sudo ScreenCapture -t jpg -x $defaultName
