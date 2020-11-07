# DeathExplode
プレイヤー死亡時に爆発するプラグインです

## コマンド
/de power <数値> : 爆発の強さを変更します(小数可)<br>
/de fire <true|false> : 爆発時の炎上の有無を変更します<br>
/de break <true|false> : 爆発時のブロック破壊の有無を変更します<br>
/de player <true|false> : プレイヤー死亡時の爆発の有無を変更します<br>
/de mob <true|false> : Mob死亡時の爆発の有無を変更します<br>
/de settings : 現在の設定を表示します<br>

## Config.ymlの使い方
※コマンドでの設定を推奨します<br>
power: 4.0		//爆発の強さ(4.0がTNTと同等)<br>
fire: false		//爆発時に炎上(trueで炎上)<br>
breakblock: true	//爆発時にブロックを破壊(trueで破壊)<br>
player: true		//プレイヤー死亡時に爆発(trueで爆発)<br>
mob: true		//Mob死亡時に爆発(trueで爆発)<br>
