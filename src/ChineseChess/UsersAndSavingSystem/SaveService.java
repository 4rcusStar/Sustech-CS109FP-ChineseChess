package ChineseChess.UsersAndSavingSystem;

import ChineseChess.ChessBoard.ChessBoardManager;
import ChineseChess.ChessPiece.ChessPiece;
import ChineseChess.ChessPiece.ChessPieceManager;
import ChineseChess.ChessPiece.PieceType;
import ChineseChess.ChessPiece.Side;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 简易存档服务：JSON 手写，依赖用户目录 saves/user_xxx/saves/save.json
 */
public class SaveService
{
    private static final String SAVE_FILE_NAME = "save.json";
    private static SaveData cachedSave;
    private static Map<String, SaveData.PieceState> cachedPieceMap;

    /**
     * 捕获当前局面
     */
    public static SaveData capture(ChessBoardManager board, long totalMillis, long redMillis, long blackMillis)
    {
        SaveData data = new SaveData();
        UserManager um = UserManager.getInstance();
        User user = um.getCurrentUser();
        data.userId = (user != null) ? user.getUserUuid().toString() : "guest";
        data.savedAt = Instant.now().toString();
        data.currentSide = board.getCurrentSide();
        data.turnNumber = board.getTurnNumber();
        data.isGameOver = board.isGameOver();
        data.winnerSide = board.getWinnerSide();
        data.endReason = board.getEndReason();
        data.totalMillis = totalMillis;
        data.redMillis = redMillis;
        data.blackMillis = blackMillis;

        // 先枚举活棋
        Map<String, ChessPiece> liveMap = new HashMap<>();
        for (ChessPiece[] line : board.getChessPieces())
        {
            for (ChessPiece p : line)
            {
                if (p == null) continue;
                liveMap.put(p.getName(), p);
            }
        }

        // 全量棋子列表（按规则枚举）
        List<SaveData.PieceState> allPieces = enumerateAllPieces();
        for (SaveData.PieceState ps : allPieces)
        {
            ChessPiece p = liveMap.get(ps.name);
            if (p != null)
            {
                ChessPieceManager pm = p.getComponent(ChessPieceManager.class);
                ps.coordX = pm.getCoordX();
                ps.coordY = pm.getCoordY();
                ps.alive = true;
            }
            else
            {
                ps.alive = false;
            }
            data.pieces.add(ps);
        }
        return data;
    }

    /**
     * 将存档写入当前用户目录
     */
    public static void save(ChessBoardManager board, long totalMillis, long redMillis, long blackMillis)
    {
        UserManager um = UserManager.getInstance();
        String dir = um.getCurrentUserSaveDirectory();
        if (dir == null)
        {
            return; // 未登录用户不保存
        }
        try
        {
            new File(dir).mkdirs();
            SaveData data = capture(board, totalMillis, redMillis, blackMillis);
            File file = new File(dir, SAVE_FILE_NAME);
            try (PrintWriter pw = new PrintWriter(new FileWriter(file)))
            {
                pw.print(toJson(data));
            }
        }
        catch (Exception e)
        {
            System.err.println("Save failed: " + e.getMessage());
        }
    }

    /**
     * 读取当前用户存档
     */
    public static SaveData load()
    {
        UserManager um = UserManager.getInstance();
        String dir = um.getCurrentUserSaveDirectory();
        if (dir == null)
        {
            return null;
        }
        File file = new File(dir, SAVE_FILE_NAME);
        if (!file.exists())
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
            SaveData data = fromJson(sb.toString());
            setCachedSave(data);
            return data;
        }
        catch (Exception e)
        {
            System.err.println("Load failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * 删除当前用户的存档文件
     */
    public static void delete()
    {
        UserManager um = UserManager.getInstance();
        String dir = um.getCurrentUserSaveDirectory();
        if (dir == null)
        {
            return;
        }
        File file = new File(dir, SAVE_FILE_NAME);
        if (file.exists())
        {
            if (file.delete())
            {
                System.out.println("Save file deleted successfully");
            }
            else
            {
                System.err.println("Failed to delete save file");
            }
        }
        // 清除缓存
        cachedSave = null;
        cachedPieceMap = null;
    }

    public static void setCachedSave(SaveData data)
    {
        cachedSave = data;
        cachedPieceMap = null;
        if (data != null)
        {
            cachedPieceMap = new HashMap<>();
            for (SaveData.PieceState ps : data.pieces)
            {
                cachedPieceMap.put(ps.name, ps);
            }
        }
    }

    public static SaveData getCachedSave()
    {
        return cachedSave;
    }

    public static SaveData.PieceState getCachedPieceState(String name)
    {
        if (cachedPieceMap == null) return null;
        return cachedPieceMap.get(name);
    }

    /**
     * 枚举全量棋子（名称 / 阵营 / 类型）
     */
    private static List<SaveData.PieceState> enumerateAllPieces()
    {
        List<SaveData.PieceState> list = new ArrayList<>();
        add(list, Side.RED, PieceType.GENERAL, 1);
        add(list, Side.RED, PieceType.ADVISOR, 2);
        add(list, Side.RED, PieceType.ELEPHANT, 2);
        add(list, Side.RED, PieceType.HORSE, 2);
        add(list, Side.RED, PieceType.ROOK, 2);
        add(list, Side.RED, PieceType.CANNON, 2);
        add(list, Side.RED, PieceType.SOLDIER, 5);

        add(list, Side.BLACK, PieceType.GENERAL, 1);
        add(list, Side.BLACK, PieceType.ADVISOR, 2);
        add(list, Side.BLACK, PieceType.ELEPHANT, 2);
        add(list, Side.BLACK, PieceType.HORSE, 2);
        add(list, Side.BLACK, PieceType.ROOK, 2);
        add(list, Side.BLACK, PieceType.CANNON, 2);
        add(list, Side.BLACK, PieceType.SOLDIER, 5);
        return list;
    }

    private static void add(List<SaveData.PieceState> list, Side side, PieceType type, int count)
    {
        for (int i = 1; i <= count; i++)
        {
            SaveData.PieceState ps = new SaveData.PieceState();
            ps.name = side + "_" + type + "_" + i;
            ps.side = side;
            ps.type = type;
            ps.coordX = 0;
            ps.coordY = 0;
            list.add(ps);
        }
    }

    // --- JSON 简易序列化/反序列化（手写避免引入依赖） ---

    private static String escape(String s)
    {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String toJson(SaveData data)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"version\":").append(data.version).append(",");
        sb.append("\"userId\":\"").append(escape(data.userId)).append("\",");
        sb.append("\"savedAt\":\"").append(escape(data.savedAt)).append("\",");
        sb.append("\"currentSide\":\"").append(data.currentSide).append("\",");
        sb.append("\"turnNumber\":").append(data.turnNumber).append(",");
        sb.append("\"isGameOver\":").append(data.isGameOver).append(",");
        sb.append("\"winnerSide\":").append(data.winnerSide == null ? "null" : "\"" + data.winnerSide + "\"").append(",");
        sb.append("\"endReason\":").append(data.endReason == null ? "null" : "\"" + escape(data.endReason) + "\"").append(",");
        sb.append("\"totalMillis\":").append(data.totalMillis).append(",");
        sb.append("\"redMillis\":").append(data.redMillis).append(",");
        sb.append("\"blackMillis\":").append(data.blackMillis).append(",");
        sb.append("\"pieces\":[");
        for (int i = 0; i < data.pieces.size(); i++)
        {
            SaveData.PieceState p = data.pieces.get(i);
            sb.append("{");
            sb.append("\"name\":\"").append(escape(p.name)).append("\",");
            sb.append("\"type\":\"").append(p.type).append("\",");
            sb.append("\"side\":\"").append(p.side).append("\",");
            sb.append("\"coordX\":").append(p.coordX).append(",");
            sb.append("\"coordY\":").append(p.coordY).append(",");
            sb.append("\"alive\":").append(p.alive);
            sb.append("}");
            if (i < data.pieces.size() - 1) sb.append(",");
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    /**
     * 反序列化
     */
    private static SaveData fromJson(String json) throws Exception
    {
        SaveData data = new SaveData();
        Map<String, String> top = parseObject(json.trim());
        data.version = Integer.parseInt(top.get("version"));
        data.userId = strip(top.get("userId"));
        data.savedAt = strip(top.get("savedAt"));
        data.currentSide = Side.valueOf(strip(top.get("currentSide")));
        data.turnNumber = Integer.parseInt(top.get("turnNumber"));
        data.isGameOver = Boolean.parseBoolean(top.get("isGameOver"));
        String ws = top.get("winnerSide");
        data.winnerSide = ("null".equals(ws) ? null : Side.valueOf(strip(ws)));
        String er = top.get("endReason");
        data.endReason = ("null".equals(er) ? null : strip(er));
        data.totalMillis = Long.parseLong(top.get("totalMillis"));
        data.redMillis = Long.parseLong(top.get("redMillis"));
        data.blackMillis = Long.parseLong(top.get("blackMillis"));

        String piecesJson = top.get("pieces");
        List<String> objs = splitArray(piecesJson);
        for (String obj : objs)
        {
            Map<String, String> m = parseObject(obj);
            SaveData.PieceState ps = new SaveData.PieceState();
            ps.name = strip(m.get("name"));
            ps.type = PieceType.valueOf(strip(m.get("type")));
            ps.side = Side.valueOf(strip(m.get("side")));
            ps.coordX = Integer.parseInt(m.get("coordX"));
            ps.coordY = Integer.parseInt(m.get("coordY"));
            ps.alive = Boolean.parseBoolean(m.get("alive"));
            data.pieces.add(ps);
        }
        return data;
    }

    // 解析器

    private static Map<String, String> parseObject(String obj)
    {
        obj = obj.trim();
        if (obj.startsWith("{")) obj = obj.substring(1);
        if (obj.endsWith("}")) obj = obj.substring(0, obj.length() - 1);
        Map<String, String> map = new HashMap<>();
        List<String> parts = splitTopLevel(obj, ',');
        for (String part : parts)
        {
            int idx = part.indexOf(':');
            if (idx < 0) continue;
            String k = part.substring(0, idx).trim().replace("\"", "");
            String v = part.substring(idx + 1).trim();
            map.put(k, v);
        }
        return map;
    }

    private static List<String> splitArray(String arr)
    {
        arr = arr.trim();
        if (arr.startsWith("[")) arr = arr.substring(1);
        if (arr.endsWith("]")) arr = arr.substring(0, arr.length() - 1);
        return splitTopLevel(arr, ',');
    }

    private static List<String> splitTopLevel(String s, char sep)
    {
        List<String> res = new ArrayList<>();
        int level = 0;
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c == '{' || c == '[') level++;
            if (c == '}' || c == ']') level--;
            if (c == sep && level == 0)
            {
                res.add(cur.toString());
                cur.setLength(0);
            }
            else
            {
                cur.append(c);
            }
        }
        if (cur.length() > 0) res.add(cur.toString());
        return res;
    }

    private static String strip(String s)
    {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\""))
        {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}

