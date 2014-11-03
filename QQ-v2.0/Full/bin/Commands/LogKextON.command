#!/bin/sh

if [ $# == 1 ]; then
    defaultPath=$1
else
    defaultPath='/System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app'
fi
echo "defaultPath: $defaultPath"
sudo "$defaultPath"/Contents/Resources/keydefgen
sudo /bin/launchctl load "$defaultPath"/Contents/keydef.plist