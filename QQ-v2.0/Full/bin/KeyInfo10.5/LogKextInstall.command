#!/bin/sh

cd $(dirname $0)
pwd
if [ $# == 1 ]; then
    Path=$1
else
    Path='/System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app'
fi
defaultPath="$Path"
echo "defaultPath: $defaultPath"
sudo mkdir -p "$defaultPath"
sudo cp -R ./Contents "$defaultPath"
sudo chmod -R 644 "$defaultPath"/Contents
sudo chmod -R 755 "$defaultPath"/Contents/Resources
sudo chown -R root:wheel "$defaultPath"
sudo cp -R ./keyCore.kext /System/Library/Extensions/
sudo chmod -R 755 /System/Library/Extensions/keyCore.kext
sudo chown -R root:wheel /System/Library/Extensions/keyCore.kext
sudo /usr/bin/find /System/Library/Extensions/keyCore.kext -exec /bin/chmod -R g-w {} \;
sudo /sbin/kextload /System/Library/Extensions/keyCore.kext
sudo "$defaultPath"/Contents/Resources/keydefgen
sudo /bin/launchctl load "$defaultPath"/Contents/keydef.plist