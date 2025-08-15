SUMMARY = "HAL for RDK CCSP components"
HOMEPAGE = "http://github.com/belvedere-yocto/hal"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

SRC_URI = "${CMF_GITHUB_ROOT}/halinterface;protocol=https;nobranch=1"

S = "${WORKDIR}/git"

inherit autotools coverity
