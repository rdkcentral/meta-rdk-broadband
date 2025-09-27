DEPENDS_append_broadband = " ccsp-common-library utopia"
LDFLAGS_broadband = " -lccsp_common -lsyscfg"


INCLUDE_DIRS_broadband = " \
     -I${STAGING_INCDIR}/ccsp \
     -I${STAGING_INCDIR}/syscfg \
     "


do_install_append_broadband () {
    install -m 0755 ${S}/scripts/uploadRDKBRRDLogs.sh ${D}${base_libdir}/rdk/uploadRRDLogs.sh
    install -m 0644 ${S}/scripts/remote-debugger.path ${D}${systemd_unitdir}/system

    sed -i -- 's/After=tr69hostif.service.*/After=rbus.service CcspPandMSsp.service utopia.service/g' ${D}${systemd_unitdir}/system/remote-debugger.service
    sed -i -- '/Requires=tr69hostif.service/d' ${D}${systemd_unitdir}/system/remote-debugger.service
    sed -i -- '/ExecStop=/,$d' ${D}${systemd_unitdir}/system/remote-debugger.service
}

SYSTEMD_SERVICE_${PN}_broadband += "remote-debugger.path"
FILES_${PN}_append_broadband += "${systemd_unitdir}/system/remote-debugger.path"
