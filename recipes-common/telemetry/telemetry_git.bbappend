DEPENDS += " ccsp-common-library webconfig-framework libunpriv dbus mountutils libsyswrapper rdkcertconfig"

LDFLAGS_append = " \
        -lprivilege \
        -ldbus-1 \
       "

CFLAGS += " -DCCSP_SUPPORT_ENABLED \
            -DENABLE_RDKB_SUPPORT \
            -DFEATURE_SUPPORT_WEBCONFIG \
            -DDROP_ROOT_PRIV \
          "

require recipes-ccsp/ccsp/ccsp_common.inc

DEPENDS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' safec', " ", d)}"

CFLAGS_append = " -I${STAGING_INCDIR}/ccsp "
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec',  ' `pkg-config --cflags libsafec`', '-fPIC', d)}"
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', '', ' -DSAFEC_DUMMY_API', d)}"
LDFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' `pkg-config --libs libsafec`', '', d)}"

do_compile_prepend () {
    (${PYTHON} ${STAGING_BINDIR_NATIVE}/dm_pack_code_gen.py ${S}/config/TR181-T2-USGv2.XML ${S}/source/t2ssp/dm_pack_datamodel.c)
}

do_install_append () {
    install -d ${D}/usr/ccsp/telemetry
    install -m 644 ${S}/config/T2Agent.cfg ${D}/usr/ccsp/telemetry
    install -m 644 ${S}/config/CcspDmLib.cfg ${D}/usr/ccsp/telemetry
    
}

FILES_${PN}_append = " \
    ${prefix}/ccsp/telemetry/T2Agent.cfg \
    ${prefix}/ccsp/telemetry/CcspDmLib.cfg \
"

EXTRA_OECONF += " --enable-ccspsupport --enable-libsyswrapper"
EXTRA_OECONF_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'gtestapp', '--enable-gtestapp', '', d)}"
EXTRA_OECONF += " --enable-mountutils --enable-rdkcertselector"
