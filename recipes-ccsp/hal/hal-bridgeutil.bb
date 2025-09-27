SUMMARY = "HAL for RDK CCSP components"
HOMEPAGE = "http://github.com/belvedere-yocto/hal"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../../LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

PROVIDES = "hal-bridgeutil"
RPROVIDES_${PN} = "hal-bridgeutil"

DEPENDS += "rdkb-halif-bridge-util"

SRC_URI = "${CMF_GITHUB_ROOT}/hardware-abstraction-layer;protocol=https;nobranch=1;name=bridgeutilhal"
SRCREV_FORMAT = "bridgeutilhal"

S = "${WORKDIR}/git/source/bridgeutil"

CFLAGS_append = " -I=${includedir}/ccsp "

CFLAGS_append += " ${@bb.utils.contains('DISTRO_FEATURES', 'rdkb_extender', '-DRDKB_EXTENDER_ENABLED', '', d)}"
DEPENDS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'core-net-lib', ' core-net-lib', " ", d)}"
CFLAGS_append  = " ${@bb.utils.contains('DISTRO_FEATURES', 'core-net-lib', ' -DCORE_NET_LIB', '', d)}"

inherit autotools coverity
