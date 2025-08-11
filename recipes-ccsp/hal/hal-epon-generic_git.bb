SUMMARY = "HAL for RDK CCSP components"
HOMEPAGE = "http://github.com/belvedere-yocto/hal"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../../LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

PROVIDES = "hal-epon"
RPROVIDES_${PN} = "hal-epon"

DEPENDS += "halinterface"

SRC_URI = "${CMF_GITHUB_ROOT}/hardware-abstraction-layer;protocol=https;nobranch=1;name=eponhal"
SRCREV_FORMAT = "eponhal"

S = "${WORKDIR}/git/source/epon"

CFLAGS_append = " -I=${includedir}/ccsp "

inherit autotools coverity
