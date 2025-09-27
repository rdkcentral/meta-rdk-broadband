SUMMARY = "DPP HAL for RDK CCSP components"
HOMEPAGE = "http://github.com/belvedere-yocto/hal"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=5d50b1d1fb741ca457897f9e370bc747"

PROVIDES = "rdk-wifi-util"
RPROVIDES_${PN} = "rdk-wifi-util"

DEPENDS += "openssl rdk-wifi-halif"
# To trigger builds, change the SRC_URI to point to forked version in github with correct BRANCH where
# the changes are merged before creating a pull request to github.com/rdkcentral/rdk-wifi-hal
SRC_URI = "git://github.com/rdkcentral/rdk-wifi-hal.git;protocol=https;branch=main;name=rdk-wifi-util"

SRCREV_rdk-wifi-util = "${AUTOREV}"
SRCREV_FORMAT = "rdk-wifi-util"

PV = "${RDK_RELEASE}+git${SRCPV}"
S = "${WORKDIR}/git/util"

CFLAGS_append = " -I=${includedir}/ccsp "

inherit autotools
