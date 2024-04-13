export interface CompletedExercise {
    id: number;
    type: string;
    duration: number;
    intensity: number;
    dayOfCompletion: string;
    weightLoss: number;
    resultDescription: string | null; 
    userId: number; // Represents the user associated with this completed exercise (Key for individual stat tracking)
  }
  