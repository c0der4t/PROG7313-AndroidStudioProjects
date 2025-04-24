# PROG7313-AndroidStudioProjects
Projects worked on during class sessions in PROG 7313

# A note on the github actions
My github action yml points to a sub folder from the root folder.
If your app folder and gradlew files are at the root of your repo, then ensure to:
- Remove any lines like this:
`working-directory: ./mygithub`

- Remove this from the artifact paths
`./mygithub/`
