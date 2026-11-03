# BetterSuicide
### A better version of OldSchoolKill, made for anarchy servers
<p align="center">
  <a href="https://modrinth.com/plugin/bettersuicide">
    <img src="https://img.shields.io/modrinth/v/bettersuicide?logo=modrinth&label=Modrinth&color=00AF5C">
  </a>
  <a href="https://www.spigotmc.org/resources/bettersuicide.133056/">
    <img src="https://img.shields.io/badge/SpigotMC-BetterSuicide-orange?logo=spigotmc&logoColor=white">
  </a>
  <a href="https://discord.gg/sACFWrPDkF">
    <img src="https://img.shields.io/discord/1466280177335013533?label=Discord&color=5865F2&logo=discord&logoColor=white" alt="Discord">
  </a>
</p>

[![bstats](https://bstats.org/signatures/bukkit/BetterSuicide.svg)](https://bstats.org/plugin/bukkit/BetterSuicide/35098)

## Features

* /kill and /suicide enabled for regular players
* Optional permission to use /kill and /suicide
* Optional cooldown system with a permission to bypass it
* Optional cooldown message when a player is on cooldown
* Optional global chat message when a player kills themselves
* Custom aliases for /suicide (requires a restart to apply)
* Automatic redirection of /kill to /minecraft:kill for operators, so admins keep the vanilla command
* MiniMessage formatting support for the prefix and every message
* Compatible with Paper 1.21+
___

## Commands
* /bettersuicide
* /bettersuicide reload
* /suicide
* /kill
___

## Permissions
* bettersuicide.main - Allows using /bettersuicide and /bettersuicide reload (default: op)
* bettersuicide.suicide - Allows using /suicide and /kill when `use-permission` is enabled (default: op)
* bettersuicide.bypass - Bypasses the cooldown when `enable-cooldown` is enabled (default: op)
