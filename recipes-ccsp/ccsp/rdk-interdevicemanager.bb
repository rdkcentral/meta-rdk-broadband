SUMMARY = "RDK Inter-Device Manager component"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"


DEPENDS = "ccsp-common-library rdk-logger utopia libunpriv"
DEPENDS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' safec', " ", d)}"

SRC_URI = "git://github.com/rdkcentral/RdkInterDeviceManager.git;protocol=https;branch=main;name=InterDeviceManager"

SRC_URI += " \
    file://RdkInterDeviceManager.conf \
    file://idm_recovery.sh \
"

SRCREV_InterDeviceManager = "v2.0.0"
SRCREV_FORMAT = "InterDeviceManager"

PV = "${RDK_RELEASE}+git${SRCPV}"

S = "${WORKDIR}/git"

require ccsp_common.inc

inherit autotools pkgconfig systemd

CFLAGS_append = " \
    -I${STAGING_INCDIR} \
    -I${STAGING_INCDIR}/dbus-1.0 \
    -I${STAGING_LIBDIR}/dbus-1.0/include \
    -I${STAGING_INCDIR}/ccsp \
    "

LDFLAGS += " -lprivilege"
LDFLAGS_append = " -ldbus-1"
LDFLAGS_remove_morty = " -ldbus-1"
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec',  ' `pkg-config --cflags libsafec`', '-fPIC', d)}"
LDFLAGS_append_dunfell = "${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' -lsafec-3.5.1 ', '', d)}"
LDFLAGS_append_kirkstone = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' -lsafec ', '', d)}"
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', '', ' -DSAFEC_DUMMY_API', d)}"
CFLAGS_prepend += " ${@bb.utils.contains('DISTRO_FEATURES', 'IDM_DEBUG',' -DIDM_DEBUG','', d)}"

EXTRA_OECONF_append  = " --with-ccsp-platform=bcm --with-ccsp-arch=arm "

SYSTEMD_SERVICE_${PN} = "RdkInterDeviceManager.service"

do_configure_prepend() {
    cp ${WORKDIR}/RdkInterDeviceManager.conf ${S}/systemd_units/
    cp ${WORKDIR}/idm_recovery.sh ${S}/source/InterDeviceManager/
}

do_install_append () {
    # Config files and scripts
    install -d ${D}${exec_prefix}/rdk/interdevicemanager
    ln -sf ${bindir}/interdevicemanager ${D}${exec_prefix}/rdk/interdevicemanager/interdevicemanager
    #Install systemd unit.
    install -d ${D}${systemd_unitdir}/system
    install -D -m 0644 ${S}/systemd_units/RdkInterDeviceManager.service ${D}${systemd_unitdir}/system/RdkInterDeviceManager.service
    install -d -m 0755 ${D}${sysconfdir}/idm
    install -D -m 0755 ${S}/source/InterDeviceManager/idm_recovery.sh ${D}${sysconfdir}/idm/idm_recovery.sh
    install -D -m 0644 ${S}/systemd_units/ssl.conf ${D}${sysconfdir}/idm/ssl.conf
    
}


FILES_${PN} = " \
   ${bindir}/* \
   ${exec_prefix}/rdk/interdevicemanager/* \
   ${systemd_unitdir}/system/RdkInterDeviceManager.service \
   ${sysconfdir}/idm/idm_recovery.sh \
   ${sysconfdir}/idm/ssl.conf \
"

FILES_${PN}-dbg = " \
    ${exec_prefix}/rdk/rdkinterdevicemanager/.debug \
    /usr/src/debug \
    ${bindir}/.debug \
    ${libdir}/.debug \
"
