SUMMARY = "HAL for RDK CCSP components"
HOMEPAGE = "http://github.com/belvedere-yocto/hal"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../../LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

PROVIDES = "hal-dhcpv4c"
RPROVIDES_${PN} = "hal-dhcpv4c"

DEPENDS += "rdkb-halif-dhcp safec-common-wrapper"
DEPENDS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' safec', " ", d)}"

SRC_URI = "${CMF_GITHUB_ROOT}/hardware-abstraction-layer;protocol=https;nobranch=1;name=dhcpv4hal"
SRCREV_FORMAT = "dhcpv4hal"

S = "${WORKDIR}/git/source/dhcpv4c"

CFLAGS_append = " -I=${includedir}/ccsp "
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec',  ' `pkg-config --cflags libsafec`', '-fPIC', d)}"
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', '', ' -DSAFEC_DUMMY_API', d)}"
LDFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' `pkg-config --libs libsafec`', '', d)}"

inherit autotools coverity pkgconfig
