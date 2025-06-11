DESCRIPTION = "Wifi Emulator Application"
LICENSE = "CLOSED"


DEPENDS = "rdk-wifi-emulator-hal rdk-wifi-libhostap ccsp-one-wifi halinterface linux-libc-headers libnl rbus"

DEPENDS_remove_bananapi4-rdk-broadband = "rdk-wifi-emulator-hal"
DEPENDS_remove_raspberrypi4-64-rdk-broadband = "rdk-wifi-emulator-hal"

DEPENDS += "${@bb.utils.contains("MACHINE", "bananapi4-rdk-broadband", "rdk-wifi-hal", "", d)}"
DEPENDS += "${@bb.utils.contains("MACHINE", "raspberrypi4-64-rdk-broadband", "rdk-wifi-hal", "", d)}"

SRC_URI = "${RDK_CPC_ROOT_GIT}/OneWifiTestSuite;protocol=${RDK_GIT_PROTOCOL};branch=${RDK_GIT_BRANCH};name=WifiEmulator"

S = "${WORKDIR}/git"
SRCREV = "${AUTOREV}"
LDFLAGS += " -L ${STAGING_LIBDIR}"

LDFLAGS_append = " -lcjson -lcurl -lrbus -lsyscfg"

CXXFLAGS_append = " -I${STAGING_INCDIR}/libnl3 "
CXXFLAGS_append = " -I${STAGING_INCDIR}/ccsp "
CXXFLAGS_append = " -I${STAGING_INCDIR}/rdk-wifi-libhostap/src "
CXXFLAGS_append = " -I${STAGING_INCDIR}/rbus "
CXXFLAGS_append = " -DWIFI_HAL_VERSION_3 "
CXXFLAGS_append_tchxb7 += "  -DCONFIG_XB7_MTLS "
CXXFLAGS_append_xb10 += "  -DCONFIG_XB7_MTLS "
EXTRA_OECMAKE_append_bananapi4-rdk-broadband  = " -DCONFIG_EXT_AGENT_CCI=ON"
CXXFLAGS_append_bananapi4-rdk-broadband = "  -DCONFIG_EXT_AGENT_CCI "
EXTRA_OECMAKE_append_raspberrypi4-64-rdk-broadband  = " -DCONFIG_EXT_AGENT_CCI=ON"
CXXFLAGS_append_raspberrypi4-64-rdk-broadband = "  -DCONFIG_EXT_AGENT_CCI "

inherit cmake

inherit systemd pkgconfig

do_install_append()  {
    install -d ${D}${systemd_unitdir}/system
    install -d ${D}${bindir}
    install -m 0644 ${S}/scripts/rdkfmac.service ${D}${systemd_unitdir}/system/rdkfmac.service
    install -m 0755 ${S}/scripts/rdkfmac_modprobe.sh ${D}${bindir}/rdkfmac_modprobe.sh
}

SYSTEMD_SERVICE_${PN} += "rdkfmac.service"
FILES_${PN} += "${systemd_unitdir}/system/rdkfmac.service"

SYSTEMD_SERVICE_${PN}_remove_raspberrypi4-64-rdk-broadband = "rdkfmac.service"
SYSTEMD_SERVICE_${PN}_remove_bananapi4-rdk-broadband = "rdkfmac.service"

FILES_${PN}_remove_raspberrypi4-64-rdk-broadband = "${systemd_unitdir}/system/rdkfmac.service"
FILES_${PN}_remove_bananapi4-rdk-broadband = "${systemd_unitdir}/system/rdkfmac.service"

FILES_${PN} += " \
        ${bindir}/* \
        ${base_bindir_native}/* \
        ${base_bindir}/* \
        ${systemd_unitdir}/system/* \
    "
