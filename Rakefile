PROJ_NAME = "awtybots-lib"
LIB_NAME = "hoist"

task :clean do
  sh "rm -rf build/ bin/ ./#{LIB_NAME}*.jar"
end

task :release do
  version = %x(git tag --list | head -n 1)[0..-2]
  sh "git checkout #{version}"
  sh "./gradlew build"
  cp("build/libs/#{PROJ_NAME}.jar","./#{LIB_NAME}-#{version}.jar")
  sh "git checkout master"
end
