SUMMARY = "jQuery is a fast, small, and feature-rich JavaScript library"
HOMEPAGE = "https://jquery.com/"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
SECTION = "devel"
LIC_FILES_CHKSUM = "file://${S}/${BP}.js;beginline=5;endline=7;md5=9c7c6e9ab275fc1e0d99cb7180ecd14c"

# unpack items to ${S} so the archiver can see them
#
SRC_URI = "\
    https://code.jquery.com/${BP}.js;name=js;subdir=${BP} \
    "

SRC_URI[js.sha256sum] = "78a85aca2f0b110c29e0d2b137e09f0a1fb7a8e554b499f740d6744dc8962cfe"

UPSTREAM_CHECK_REGEX = "jquery-(?P<pver>\d+(\.\d+)+)\.js"

# https://github.com/jquery/jquery/issues/3927
CVE_STATUS[CVE-2007-2379] = "upstream-wontfix: There are ways jquery can expose security issues but any issues \
are in the apps exposing them and there is little we can directly do."

inherit allarch

do_install() {
    install -d ${D}/usr/www2/cmn/js/lib
    install -m 0644 ${S}/jquery-3.7.1.js ${D}/usr/www2/cmn/js/lib
}

PACKAGES = "${PN}"
FILES_${PN} = "${datadir}"
FILES_${PN} += "/usr/www2/cmn/js/lib"

BBCLASSEXTEND += "native nativesdk"

