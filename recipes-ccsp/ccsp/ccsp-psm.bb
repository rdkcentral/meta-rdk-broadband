SUMMARY = "CCSP PsmSsp component"
HOMEPAGE = "http://github.com/belvedere-yocto/CcspPsm"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"


DEPENDS = "ccsp-common-library dbus rbus utopia libunpriv mountutils"
DEPENDS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', '', d)}"
DEPENDS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' safec', " ", d)}"

require ccsp_common.inc

SRC_URI = "${CMF_GITHUB_ROOT}/persistent-storage-manager;protocol=https;nobranch=1"

S = "${WORKDIR}/git"

inherit autotools breakpad-logmapper

CFLAGS += " -Wall -Werror -Wextra -Wno-misleading-indentation"

CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec',  ' `pkg-config --cflags libsafec`', '-fPIC', d)}"

LDFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' `pkg-config --libs libsafec`', '', d)}"
LDFLAGS_remove = "${@bb.utils.contains('DISTRO_FEATURES', 'safec', '-lsafec-3.5', '', d)}"
LDFLAGS_append_dunfell = "${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' -lsafec-3.5.1 ', '', d)}"
LDFLAGS_append_kirkstone = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', ' -lsafec ', '', d)}"
CFLAGS_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'safec', '', ' -DSAFEC_DUMMY_API', d)}"

LDFLAGS +=" -lprivilege -lsyscfg -lsecure_wrapper"
EXTRA_OECONF_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '--enable-notify', '', d)}"

CFLAGS_append = " \
    -I=${includedir}/dbus-1.0 \
    -I=${libdir}/dbus-1.0/include \
    -I=${includedir}/ccsp \
    -I=${includedir}/rbus \
    -I${STAGING_INCDIR}/syscfg \
    "

LDFLAGS_append = " \
    -ldbus-1 \
    "

do_install_append () {
    # Config files and scripts
    install -d ${D}/usr/ccsp
    install -d ${D}/usr/ccsp/psm
    ln -sf /usr/bin/PsmSsp ${D}/usr/ccsp/PsmSsp
}

do_install_append_qemuarm () {
    # Config files and scripts
    install -d ${D}/usr/ccsp/config
    install -m 644 ${S}/config/bbhm_def_cfg_qemu.xml ${D}/usr/ccsp/config/bbhm_def_cfg.xml
}

PACKAGES += "${PN}-ccsp"

FILES_${PN}-ccsp = " \
    ${prefix}/ccsp/psm \
    ${prefix}/ccsp/PsmSsp \
    ${prefix}/ccsp/config/bbhm_def_cfg.xml \
"

FILES_${PN}-dbg = " \
    ${prefix}/ccsp/.debug \
    ${prefix}/src/debug \
    ${bindir}/.debug \
    ${libdir}/.debug \
"

# Breakpad processname and logfile mapping
BREAKPAD_LOGMAPPER_PROCLIST = "PsmSsp"
BREAKPAD_LOGMAPPER_LOGLIST = "PSMlog.txt.0"

EXTRA_OECONF_append += "${@bb.utils.contains("DISTRO_FEATURES", "MountUtils", "--enable-mountutils=yes", " ", d)}"
