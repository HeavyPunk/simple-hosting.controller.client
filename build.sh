BUILD_ARTIFACT_PATH=./target/scala-2.13/*.jar

rm -rf target

sbt package

rm -rf build

mkdir ./build
cp $BUILD_ARTIFACT_PATH ./build/