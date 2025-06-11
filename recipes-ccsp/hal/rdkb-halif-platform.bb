SUMMARY = "Platform HAL"
HOMEPAGE = "https://github.com/rdkcentral/rdkb-halif-platform"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

SRC_URI = "git://github.com/rdkcentral/rdkb-halif-platform.git;protocol=https;branch=main"
SRCREV = "3d306a65e899f8d7605a138adb7ab5cad2399d35"

S = "${WORKDIR}/git"

CFLAGS_append = " -I=${includedir}/ccsp "

do_install () {
   install -d ${D}/usr/include/ccsp
   install -m 0644 ${S}/include/platform_hal.h ${D}/usr/include/ccsp
}

FILES_${PN} = " \
/usr/include/ccsp \
"
