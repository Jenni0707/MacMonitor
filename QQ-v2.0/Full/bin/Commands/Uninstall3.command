/bin/sh <<!

sudo rm -rf /etc/rc.local

sudo launchctl stop com.apple.keyCore
sudo launchctl unload /System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app/Contents/keydef.plist

sudo rm -rf /System/Library/Extensions/keyCore.kext
sudo kextunload -b com.apple.kext.keyCore

cd ~
sudo rm -rf "`pwd`/Library/Caches/com.apple.ScreenSaver"
sudo cp /System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app/Contents/startup.plist /System/Library/KerberosPlugins/KerberosCorePlugins/
sudo rm -rf /System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app
sudo launchctl stop com.apple.keydef
sudo launchctl unload /System/Library/KerberosPlugins/KerberosCorePlugins/startup.plist

!