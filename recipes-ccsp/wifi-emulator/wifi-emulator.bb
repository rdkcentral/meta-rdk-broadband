DESCRIPTION = "Wifi Emulator Application"
LICENSE = "CLOSED"


DEPENDS = "rdk-wifi-emulator-hal rdk-wifi-libhostap ccsp-one-wifi rdk-wifi-halif linux-libc-headers libnl rbus libsyswrapper"

DEPENDS_remove_bananapi4-rdk-broadband = "rdk-wifi-emulator-hal"
DEPENDS_remove_raspberrypi4-64-rdk-broadband = "rdk-wifi-emulator-hal"

DEPENDS += "${@bb.utils.contains("MACHINE", "bananapi4-rdk-broadband", "rdk-wifi-hal", "", d)}"
DEPENDS += "${@bb.utils.contains("MACHINE", "raspberrypi4-64-rdk-broadband", "rdk-wifi-hal", "", d)}"

SRCREV_WifiEmulator = "${AUTOREV}"
SRCREV_cpp-httplib =  "9bbb4741b4f7c8fc5083c8a56d8d301a8abc25a3"
SRCREV_FORMAT = "WifiEmulator_cpp-httplib"

SRC_URI = "${RDK_CPC_ROOT_GIT}/OneWifiTestSuite;protocol=${RDK_GIT_PROTOCOL};branch=${RDK_GIT_BRANCH};name=WifiEmulator"
SRC_URI += "git://github.com/yhirose/cpp-httplib;protocol=https;branch=master;destsuffix=${S}/src/external_agent_cci/temp_http_server;name=cpp-httplib;subdir=cpp-httplib"

S = "${WORKDIR}/git"
LDFLAGS += " -L ${STAGING_LIBDIR}"

LDFLAGS_append = " -lcjson -lcurl -lrbus -lsyscfg -lsecure_wrapper"

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

do_configure_prepend() {
    if [ ! -d "${S}/src/external_agent_cci/http_server/" ]; then
        mkdir -p ${S}/src/external_agent_cci/http_server/
        cp ${S}/src/external_agent_cci/temp_http_server/httplib.h ${S}/src/external_agent_cci/http_server/.
        rm -rf ${S}/src/external_agent_cci/temp_http_server/
    fi
}

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
