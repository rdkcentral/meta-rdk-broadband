EXTRA_OECONF_append = " --enable-journalctl"

CFLAGS_append = " -DONEWIFI_OVSDB_TABLE_SUPPORT   \
                  -DONEWIFI_CSI_APP_SUPPORT       \
                  -DONEWIFI_CAC_APP_SUPPORT \
                  -DONEWIFI_MOTION_APP_SUPPORT \
                  -DONEWIFI_HARVESTER_APP_SUPPORT \
                  -DONEWIFI_ANALYTICS_APP_SUPPORT \
                  -DONEWIFI_LEVL_APP_SUPPORT \
                  -DONEWIFI_WHIX_APP_SUPPORT \
                  -DONEWIFI_BLASTER_APP_SUPPORT \
                  -DONEWIFI_RDKB_APP_SUPPORT \
                  -DONEWIFI_DB_SUPPORT \
                  -DONEWIFI_DML_SUPPORT \
                  -DONEWIFI_RDKB_CCSP_SUPPORT \
                  "


EXTRA_OECONF_append = "${@bb.utils.contains('DISTRO_FEATURES', 'sm_app', ' --enable-sm-app', '', d)}"

do_compile_append() {
    oe_runmake -C source/platform
}

do_install_append() {
    oe_runmake -C source/platform DESTDIR=${D} install
    if "${@bb.utils.contains('DISTRO_FEATURES', 'dbus_support', 'true', 'false', d)}"; then
        install -m 644 ${S}/source/platform/dbus/bus.h ${D}/usr/include/ccsp
    else
        install -m 644 ${S}/source/platform/rdkb/bus.h ${D}/usr/include/ccsp
    fi
    install -m 644 ${S}/source/platform/common/bus_common.h ${D}/usr/include/ccsp
    install -m 644 ${S}/source/platform/common/platform_common.h ${D}/usr/include/ccsp
    install -m 644 ${S}/source/platform/rdkb/misc.h ${D}/usr/include/ccsp
    install -m 644 ${S}/source/dml/rdkb/wifi_dml.h ${D}/usr/include/ccsp
    install -m 644 ${S}/source/ccsp/ccsp.h ${D}/usr/include/ccsp
}

FILES_${PN} += " \
    ${libdir}/libwifi_bus.so* \
"
