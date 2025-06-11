SUMMARY = "Ethsw HAL"
HOMEPAGE = "https://github.com/rdkcentral/rdkb-halif-ethsw"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://github.com/rdkcentral/rdkb-halif-ethsw.git;protocol=https;branch=main"
SRCREV = "0ad8eee7a163bddfbe86a52134d3d59fa40573f9"

S = "${WORKDIR}/git"

CFLAGS_append = " -I=${includedir}/ccsp "

do_install () {
   install -d ${D}/usr/include/ccsp
   install -m 0644 ${S}/include/ccsp_hal_ethsw.h ${D}/usr/include/ccsp
}

FILES_${PN} = " \
/usr/include/ccsp \
"