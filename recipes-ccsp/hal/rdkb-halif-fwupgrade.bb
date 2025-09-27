SUMMARY = "Fwupgrade HAL"
HOMEPAGE = "https://github.com/rdkcentral/rdkb-halif-fwupgrade"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://github.com/rdkcentral/rdkb-halif-fwupgrade.git;protocol=https;branch=main"
SRCREV = "3c5dea46999eee137765496646cbd30271b7922d"

S = "${WORKDIR}/git"

CFLAGS_append = " -I=${includedir}/ccsp "

do_install () {
   install -d ${D}/usr/include/ccsp
   install -m 0644 ${S}/include/fwupgrade_hal.h ${D}/usr/include/ccsp
}

FILES_${PN} = " \
/usr/include/ccsp \
"