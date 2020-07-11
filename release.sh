#!/bin/sh
set -ex

LIB_NAME="hoist"
version="$(git tag -l | head -n 1)"

git checkout "$version"
./gradlew clean build
cp build/libs/awtybots-lib.jar "./$LIB_NAME-$version.jar"
git checkout master
