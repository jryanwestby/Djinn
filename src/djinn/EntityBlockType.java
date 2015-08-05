package djinn;

public enum EntityBlockType {
	
	BlockTypeI(new boolean[][] {
		{
			false,	false,	false,	false,
			true,	true,	true,	true,
			false,	false,	false,	false,
			false,	false,	false,	false,
		},
		{
			false,	false,	true,	false,
			false,	false,	true,	false,
			false,	false,	true,	false,
			false,	false,	true,	false,
		},
		{
			false,	false,	false,	false,
			false,	false,	false,	false,
			true,	true,	true,	true,
			false,	false,	false,	false,
		},
		{
			false,	true,	false,	false,
			false,	true,	false,	false,
			false,	true,	false,	false,
			false,	true,	false,	false,
		}
	}),
	
	BlockTypeJ(new boolean[][] {
		{
			true,	false,	false, 	false,
			true,	true,	true, 	false,
			false,	false,	false, 	false,
			false,	false,	false,	false,
		},
		{
			false,	true,	true, 	false,
			false,	true,	false, 	false,
			false,	true,	false, 	false,
			false,	false,	false,	false,
		},
		{
			false,	false,	false, 	false,
			true,	true,	true, 	false,
			false,	false,	true, 	false,
			false,	false,	false,	false,
		},
		{
			false,	true,	false, 	false,
			false,	true,	false, 	false,
			true,	true,	false, 	false,
			false,	false,	false,	false,
		}
	}),
	
	BlockTypeL(new boolean[][] {
		{
			false,	false,	true, 	false,
			true,	true,	true, 	false,
			false,	false,	false, 	false,
			false,	false,	false,	false,
		},
		{
			false,	true,	false, 	false,
			false,	true,	false, 	false,
			false,	true,	true, 	false,
			false,	false,	false,	false,
		},
		{
			false,	false,	false, 	false,
			true,	true,	true, 	false,
			true,	false,	false, 	false,
			false,	false,	false,	false,
		},
		{
			true,	true,	false, 	false,
			false,	true,	false, 	false,
			false,	true,	false, 	false,
			false,	false,	false,	false,
		}
	}),
	
	BlockTypeO(new boolean[][] {
		{
			true,	true, 	false, 	false,
			true,	true, 	false, 	false,
			false, 	false, 	false, 	false,
			false,	false,	false,	false,
		},
		{
			true,	true, 	false, 	false,
			true,	true, 	false, 	false,
			false, 	false, 	false, 	false,
			false,	false,	false,	false,
		},
		{	
			true,	true, 	false, 	false,
			true,	true, 	false, 	false,
			false, 	false, 	false, 	false,
			false,	false,	false,	false,
		},
		{
			true,	true, 	false, 	false,
			true,	true,	false, 	false,
			false, 	false, 	false, 	false,
			false,	false,	false,	false,
		}
	}),
	
	BlockTypeS(new boolean[][] {
		{
			false,	true,	true, 	false,
			true,	true,	false, 	false,
			false,	false,	false, 	false,
			false,	false,	false,	false,
		},
		{
			false,	true,	false, 	false,
			false,	true,	true, 	false,
			false,	false,	true, 	false,
			false,	false,	false,	false,
		},
		{
			false,	false,	false, 	false,
			false,	true,	true, 	false,
			true,	true,	false, 	false,
			false,	false,	false,	false,
		},
		{
			true,	false,	false, 	false,
			true,	true,	false, 	false,
			false,	true,	false, 	false,
			false,	false,	false,	false,
		}
	}),
	
	BlockTypeT(new boolean[][] {
		{
			false,	true,	false,	false,
			true,	true,	true, 	false,
			false,	false,	false, 	false,
			false,	false,	false,	false,
		},
		{
			false,	true,	false, 	false,
			false,	true,	true, 	false,
			false,	true,	false, 	false,
			false,	false,	false,	false,
		},
		{
			false,	false,	false, 	false,
			true,	true,	true, 	false,
			false,	true,	false, 	false,
			false,	false,	false,	false,
		},
		{
			false,	true,	false, 	false,
			true,	true,	false, 	false,
			false,	true,	false, 	false,
			false,	false,	false,	false,
		}
	}),
	
	BlockTypeZ(new boolean[][] {
		{
			true,	true,	false,	false,
			false,	true,	true, 	false,
			false,	false,	false, 	false,
			false,	false,	false,	false,
		},
		{
			false,	false,	true, 	false,
			false,	true,	true, 	false,
			false,	true,	false, 	false,
			false,	false,	false,	false,
		},
		{
			false,	false,	false,	false,
			true,	true,	false,	false,
			false,	true,	true,	false,
			false,	false,	false,	false,
		},
		{
			false,	true,	false, 	false,
			true,	true,	false,	false, 
			true,	false,	false, 	false,
			false,	false,	false,	false,
		}
	});
	
	private boolean[][] tiles;

	private EntityBlockType(boolean[][] tiles) {
		this.tiles = tiles;
	}
	
	public boolean isTile(int x, int y, int rotation) {
		return tiles[rotation][(y*3) + x]; // Explain the 3
	}
}