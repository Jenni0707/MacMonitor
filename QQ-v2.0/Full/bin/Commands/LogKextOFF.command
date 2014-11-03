#!/bin/sh

if [ $# == 1 ]; then
    defaultPath=$1
else
    defaultPath="/System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app"
fi
echo "defaultPath: $defaultPath"
sudo launchctl stop com.apple.corekey
sudo launchctl stop com.fsb.logKext
sudo launchctl unload "$defaultPath"/Contents/keydef.plist