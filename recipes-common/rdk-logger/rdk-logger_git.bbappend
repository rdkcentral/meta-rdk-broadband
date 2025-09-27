FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " file://rdkb_log4crc "

do_configure_append () {
    install -m 644 ${WORKDIR}/rdkb_log4crc ${S}/log4crc
}

do_install_append () {
        install -d ${D}/rdklogger
        install -d ${D}/fss/gw/rdklogger
        ln -sf /etc/log4crc ${D}/rdklogger/log4crc
        ln -sf /etc/log4crc ${D}/fss/gw/rdklogger/log4crc
}

FILES_${PN} += " /rdklogger/ \
                 /fss/gw/rdklogger/ \
               "
