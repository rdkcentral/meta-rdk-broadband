RDEPENDS_packagegroup-rdk-ccsp-broadband_append = " \
		${@bb.utils.contains('DISTRO_FEATURES', 'Wifi-test-suite', 'wifi-emulator', '', d)} \
		\
"
