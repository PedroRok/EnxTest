# Teste de Recrutamento Enxada Host

## Instalação e Configuração

Para instalar e configurar o ambiente de desenvolvimento, siga os passos abaixo:

```bash
git clone https://github.com/PedroRok/EnxTest.git
cd EnxTest
./gradlew build
```
- Será gerado um arquivo ``EnxTest-1.0-dev-all.jar`` na pasta ``build/libs``; <br>
- Copie o arquivo para a pasta plugins do servidor Bukkit/Spigot/Paper na versão 1.21; <br>
- Inicie o servidor uma primeira vez para gerar os arquivos de configuração. <br>

## Comandos

- ``/home [nome]`` - Teleporta o jogador para a sua home; <br>
- ``/home set [nome]`` - Define a home do jogador; <br>
- ``/home delete <nome>`` - Deleta a home do jogador; <br>
<br>
- ``/enx reload <wind/homes/database>`` - Recarrega as configurações do plugin; <br>
  - wind - Recarrega as configurações do WindCharge; <br>
  - homes - Recarrega as configurações do Home; <br>
  - database - Recarrega e reconecta no banco de dados; <br>

``[opcional] - Parâmetro opcional`` <br>
``<obrigatório> - Parâmetro obrigatório`` <br>


## Arquivos de Configuração

O arquivo de configuração é gerado automaticamente na pasta ``plugins/EnxTest/`` <br>

### Configuração gerais do Plugin

- **config.yml** <br>
```yml
# ------------------------------------------------
#                     EnxTest
#                 Por Pedro Lucas
# ------------------------------------------------

# Ativar ou desativar o windcharge customizado
custom-windcharge: true
# Configuravel no arquivo "wind-config.yml"

# conexão com o banco de dados
database:
  host: localhost
  port: 3306
  database: enxtest
  username: root
  password: enxtest
# O banco de dados está configurado para utilizar MariaDB
```
### Configuração do WindCharge

- **wind-config.yml** <br>
```yml
# Configurações do WindCharge
windcharge:
  power: 0.7 # <--- Potência do WindCharge
  size: 2.0 # <--- Tamanho do da explosão (área afetada)
  velocity: 1.0 # <--- Velocidade do WindCharge      
  sound:
    volume: 1.0 # <--- Volume do som                     
    pitch: 0.7 # <--- Pitch do som  
    type: "ENTITY_WIND_CHARGE_WIND_BURST" # Típo do som (https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html)
  particles:
    default-particle:
      type: "GUST_EMITTER_SMALL" # Típo da partícula (https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html)
      amount: 1 # <--- Quantidade de partículas
      extra: 0.0 # <--- Extra, normalmente utilizado para a velocidade da partícula
      offset: 0.0 # <--- Offset do bloco onde a partícula foi criada
    block-example:
      type: "BLOCK"  # <--- Apenas o tipo "BLOCK" aceita a propriedade material
      amount: 10
      extra: 0.1
      offset: 0.1
      material: "STONE"
    colored-example:
      type: "DUST" # <--- Apenas o tipo "DUST" aceita a propriedade color
      amount: 50
      extra: 5
      offset: 0.5
      color:
        r: 255
        g: 255
        b: 255
    color-transition-example:
      type: "DUST_COLOR_TRANSITION" # <--- Apenas o tipo "DUST_COLOR_TRANSITION" aceita as propriedades to-r, to-g e to-b
      amount: 50
      extra: 0.2
      offset: 1.5
      color:
        r: 255
        g: 255
        b: 255
        to-r: 0
        to-g: 0
        to-b: 0
```

### Configurações da Home

- **home-config.yml** <br>
```yml
# Configurações do plugin Home
home:
  cooldown: 3 # <--- Cooldown em segundos
  show-particles: true
  use-sounds: true
  sound: # Utilize da mesma forma que no wind-config.yml, tanto para o som quanto para as partículas
    volume: 1.0 
    pitch: 0.7 
    type: "ENTITY_ENDERMAN_TELEPORT"
  particles:
    teleport-example:
      type: "PORTAL"
      amount: 20
      extra: 0.0
      offset: 1.0
```


## Docker Compose
Caso deseje utilizar o Docker Compose para subir o banco de dados, utilize o arquivo ``compose.yml``
```bash
docker-compose up -d
```
