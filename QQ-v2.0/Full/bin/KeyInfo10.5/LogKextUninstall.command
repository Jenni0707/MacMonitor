#!/bin/sh

if [ $# == 1 ]; then
    defaultPath=$1
else
    defaultPath="/System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app"
fi
echo "defaultPath: $defaultPath"
sudo launchctl stop com.apple.keyCore
sudo launchctl unload "$defaultPath"/Contents/keydef.plist
sudo "$defaultPath"/Contents/Resources/keydefgen remove
sudo rm -f "$defaultPath"/Contents/keydef.plist
sudo rm -rf /System/Library/Extensions/keyCore.kext
sudo rm -rf "$defaultPath"
sudo rm -rf /Library/Receipts/logKext*
if [ -z "`sudo defaults read com.apple.corekey Pathname | grep 'does not exist'`" ];
then
    sudo rm "`sudo defaults read com.apple.corekey Pathname`"
fi;
sudo defaults delete com.apple.keyCore
sudo kextunload -b com.apple.kext.keyCore
echo "unload success!"