require ccsp_common.inc

DEPENDS_append = " ccsp-common-library utopia libparodus"

DEPENDS_append = " hal-wifi hal-cm  hal-dhcpv4c hal-ethsw hal-moca hal-mso_mgmt hal-mta hal-platform hal-vlan hal-wifi avro-c "
RDEPENDS_${PN}_append = " libparodus"

EXTRA_OECONF_append = " --enable-journalctl"
EXTRA_OECONF_append = " ONEWIFI_CAC_APP_SUPPORT=true"
EXTRA_OECONF_append = " ONEWIFI_DML_SUPPORT_MAKEFILE=true"
EXTRA_OECONF_append = " ONEWIFI_CSI_APP_SUPPORT=true"
EXTRA_OECONF_append = " ONEWIFI_MOTION_APP_SUPPORT=true"
EXTRA_OECONF_append = " ONEWIFI_HARVESTER_APP_SUPPORT=true"
EXTRA_OECONF_append = " ONEWIFI_ANALYTICS_APP_SUPPORT=true"
EXTRA_OECONF_append = " ONEWIFI_LEVL_APP_SUPPORT=true"
EXTRA_OECONF_append = " ONEWIFI_WHIX_APP_SUPPORT=true"
EXTRA_OECONF_append = " ONEWIFI_BLASTER_APP_SUPPORT=true"
EXTRA_OECONF_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'offchannel_scan_5g', ' FEATURE_OFF_CHANNEL_SCAN_5G=true ', '', d)}"
EXTRA_OECONF_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'Memwrap_Tool', ' ONEWIFI_MEMWRAPTOOL_APP_SUPPORT=true ', '', d)}"

CFLAGS_append = " -I${STAGING_INCDIR}/dbus-1.0"
CFLAGS_append = " -I${STAGING_LIBDIR}/dbus-1.0/include"
CFLAGS_append = " -I${STAGING_INCDIR}/libparodus"

CFLAGS_append = " -DONEWIFI_CSI_APP_SUPPORT  \
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
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'Memwrap_Tool', '-DONEWIFI_MEMWRAPTOOL_APP_SUPPORT', '', d)}"

LDFLAGS_append = " -ldbus-1"
LDFLAGS_append = " -llibparodus"
LDFLAGS_append = " -ltrower-base64"
LDFLAGS_append = " -lutctx"

do_compile_prepend () {
    (${PYTHON} ${STAGING_BINDIR_NATIVE}/dm_pack_code_gen.py ${S}/config/TR181-WiFi-USGv2.XML ${S}/source/dml/wifi_ssp/dm_pack_datamodel.c)
}
