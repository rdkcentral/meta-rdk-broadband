SUMMARY = "HAL for RDK CCSP components"
HOMEPAGE = "http://github.com/belvedere-yocto/hal"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../../LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

PROVIDES = "hal-mso_mgmt"
RPROVIDES_${PN} = "hal-mso_mgmt"

DEPENDS += "rdkb-halif-mso"

SRC_URI = "${CMF_GITHUB_ROOT}/hardware-abstraction-layer;protocol=https;nobranch=1;name=msomgmthal"
SRCREV_FORMAT = "msomgmthal"

S = "${WORKDIR}/git/source/mso_mgmt"

CFLAGS_append = " -I=${includedir}/ccsp "

inherit autotools coverity
