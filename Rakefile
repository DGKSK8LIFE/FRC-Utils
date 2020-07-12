PROJ_NAME = "awtybots-lib"
LIB_NAME = "hoist"

desc "Remove gradle's build folders"
task :clean do
  sh "rm -rf build/ bin/ ./#{LIB_NAME}*.jar"
end

desc "Generate release jar for latest tag"
task :release do
  version = %x(git tag --list | head -n 1)[0..-2]
  puts "Creating release for tag: \'#{version}\'"
  sh "git checkout #{version} --quiet"
  puts "Building JAR"
  sh "./gradlew build --quiet"
  cp("build/libs/#{PROJ_NAME}.jar","./#{LIB_NAME}-#{version}.jar")
  sh "git checkout master --quiet"
end
