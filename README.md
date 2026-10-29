# MoreVanilla

MoreVanilla 是一个面向 Minecraft Java Edition 26.3 的 Fabric Mod，用于补充原版中缺少的、风格一致的建筑方块。

## 当前功能

- 平滑石头楼梯：`more_vanilla:smooth_stone_stairs`
- 平滑石头墙：`more_vanilla:smooth_stone_wall`
- 切制砂岩楼梯：`more_vanilla:cut_sandstone_stairs`
- 切制砂岩墙：`more_vanilla:cut_sandstone_wall`
- 石头墙：`more_vanilla:stone_wall`
- 石英墙：`more_vanilla:quartz_wall`
- 海晶石砖墙：`more_vanilla:prismarine_bricks_wall`
- 暗海晶石砖墙：`more_vanilla:dark_prismarine_wall`
- 紫珀块墙：`more_vanilla:purpur_block_wall`
- 磨制安山岩墙：`more_vanilla:polished_andesite_wall`
- 磨制花岗岩墙：`more_vanilla:polished_granite_wall`
- 磨制闪长岩墙：`more_vanilla:polished_diorite_wall`
- 完整支持直线、内角、外角、四方向和上下半层楼梯状态
- 支持简体中文、繁体中文（台湾）、繁体中文（香港）和英文
- 方块使用对应原版方块的材质、硬度和音效属性

## 创造物品栏顺序

建筑方块标签中的顺序按原版方块族群排列：

- 平滑石头 → 平滑石头楼梯 → 平滑石台阶 → 平滑石头墙
- 切制砂岩 → 切制砂岩楼梯 → 切制砂岩台阶 → 切制砂岩墙
- 石头、石英、海晶石砖、暗海晶石砖、紫珀块及三种磨制石墙均放在对应方块族群的台阶之后

JEI 使用独立的物品排序系统。如果需要让 JEI 按创造物品栏顺序显示，请在 JEI 客户端配置中设置：

```ini
IngredientSortStages = CREATIVE_MENU
```

## 配方

### 平滑石头楼梯

- 6 个平滑石头合成 4 个平滑石头楼梯
- 1 个平滑石头通过切石机制作 1 个平滑石头楼梯

### 平滑石头墙

- 6 个平滑石头合成 6 个平滑石头墙
- 1 个平滑石头通过切石机制作 1 个平滑石头墙

### 切制砂岩楼梯

- 6 个切制砂岩合成 4 个切制砂岩楼梯
- 1 个切制砂岩通过切石机制作 1 个切制砂岩楼梯

### 切制砂岩墙

- 6 个切制砂岩合成 6 个切制砂岩墙
- 1 个切制砂岩通过切石机制作 1 个切制砂岩墙

### 其他墙类

石头墙、石英墙、海晶石砖墙、暗海晶石砖墙、紫珀块墙、磨制安山岩墙、磨制花岗岩墙和磨制闪长岩墙均支持：

- 6 个对应方块合成 6 个墙
- 1 个对应方块通过切石机制作 1 个墙

## 安装

环境要求：

- Minecraft Java Edition 26.3
- Fabric Loader 0.20.1 或更高版本
- Fabric API 0.165.0+26.3 或更高版本
- Java 25 或更高版本

将 `MoreVanilla-1.0.1.jar` 放入 Minecraft 实例的 `mods` 文件夹，并同时安装 Fabric API。

## 构建

项目使用 Gradle Wrapper。执行：

```bash
./gradlew build
```

构建产物位于：

```text
build/libs/MoreVanilla-1.0.1.jar
```

## 项目信息

- Mod 名称：MoreVanilla
- Mod ID：`more_vanilla`
- Java 包名：`io.github.f_anthropic.more.vanilla`
- 当前版本：`1.0.3`
- GitHub：<https://github.com/F-Anthropic/MoreVanilla>

## 许可证

本项目使用 GNU Lesser General Public License v3 或更高版本（LGPL-3.0-or-later）。详情见 [LICENSE](LICENSE)。
