
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![GNU License][license-shield]][license-url]




<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/CodedRed-Spigot/XpBottles">
     <img src="logo.png" alt="Logo" width="750" height="500">
  </a>

  <h3 align="center">XpBottles</h3>

  <p align="center">
    Advanced exp bottle plugin!
    <br />
    <a href="https://github.com/CodedRed-Spigot/XpBottles"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="[site-url]">View Demo</a>
    ·
    <a href="https://github.com/CodedRed-Spigot/XpBottles/issues">Report Bug</a>
    ·
    <a href="https://github.com/CodedRed-Spigot/XpBottles/issues">Request Feature</a>
  </p>
</p>




<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
<!--    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li> -->
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#building">Building</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>




<!-- ABOUT THE PROJECT -->
## About The Project

XpBottles is the ultimate exp plugin! Create exp bottles, add/take/reset exp from players, and more! 
The exp amount is saved in a hidden location so you will be able to customize the lore/name of the item to whatever you would like!
No restrictions.




<!-- GETTING STARTED
## Getting Started -->

<!-- To get a local copy up and running follow these simple steps. -->




<!-- USAGE EXAMPLES -->
## Usage

More info coming soon!
_For more examples, please refer to the [Documentation][site-url]_




<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/CodedRed-Spigot/XpBottles/issues) for a list of proposed features (and known issues).




<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AwesomeFeature`)
3. Commit your Changes (`git commit -m 'Add some AwesomeFeature'`)
4. Push to the Branch (`git push origin feature/AwesomeFeature`)
5. Open a Pull Request

### New version support
Adding support for new Spigot versions is very simple. Using the versions that are already there, you should be able to fill in the gaps.
1. Create a new folder in the root directory with the CraftBukkit package name (e.g `v1_18_R2`).
2. Create the package path (`src/main/java/me.codedred.xpbottles.versions`).
3. Create the NMS class. The name should be `Version_<craftbukkit version>` without the `<>`.
4. Create a new `build.gradle` using other NMS versions as a template and change the spigot dependency version.
5. Open the `settings.gradle` file and add the new module.
6. Open `dist/build.gradle` and add the new version to the dependencies list.




<!-- BUILDING -->
## Building
To build the project, you need to firstly run [BuildTools](https://www.spigotmc.org/wiki/buildtools/) for all supported versions.  
Once finished, they should all be added to your local Maven repo.  
Then you can run `./gradlew build` and the final jar will be in the `build/libs` folder.




<!-- LICENSE -->
## License

Distributed under the GNU General Public License v3.0. See `LICENSE` for more information.




<!-- CONTACT -->
## Contact

CodedRed - [@twitter](https://twitter.com/devcodedred) - devcodedred@gmail.com

[@spigotmc](https://www.spigotmc.org/resources/authors/codedred.421005/)
Discord: CodedRed#0900

Project Link: [https://github.com/CodedRed-Spigot/XpBottles](https://github.com/CodedRed-Spigot/XpBottles)




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/CodedRed-Spigot/XpBottles.svg?style=for-the-badge
[contributors-url]: https://github.com/CodedRed-Spigot/XpBottles/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/CodedRed-Spigot/XpBottles.svg?style=for-the-badge
[forks-url]: https://github.com/CodedRed-Spigot/XpBottles/network/members
[stars-shield]: https://img.shields.io/github/stars/CodedRed-Spigot/XpBottles.svg?style=for-the-badge
[stars-url]: https://github.com/CodedRed-Spigot/XpBottles/stargazers
[issues-shield]: https://img.shields.io/github/issues/CodedRed-Spigot/XpBottles.svg?style=for-the-badge
[issues-url]: https://github.com/CodedRed-Spigot/XpBottles/issues
[license-shield]: https://img.shields.io/github/license/CodedRed-Spigot/XpBottles.svg?style=for-the-badge
[license-url]: https://github.com/CodedRed-Spigot/XpBottles/blob/master/LICENSE.txt
[site-url]: https://www.spigotmc.org/resources/xpbottles-convert-exp-into-bottles.69233/
