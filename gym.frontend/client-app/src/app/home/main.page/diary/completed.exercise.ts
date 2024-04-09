export interface CompletedExercise {
    id: number;
    type: string;
    duration: number;
    intensity: number;
    dayOfCompletion: string; // Assuming it's in ISO format (YYYY-MM-DD), otherwise adjust accordingly
    weightLoss: number;
    resultDescription: string | null; // Nullable string
    userId: number; // Assuming this represents the user ID associated with the completed exercise
  }
  